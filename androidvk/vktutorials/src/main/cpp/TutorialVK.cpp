#include "TutorialVK.h"
#include "vector"
#include "my_log.h"

const std::vector<const char*>validationLayers = {
        "VK_LAYER_LUNARG_standard_validation"
};

#ifdef NDEBUG
const bool enableValidationLayers = false;
#else
const bool enableValidationLayers = true;
#endif

VkResult CreateDebugReportCallbackEXT(VkInstance instance, const VkDebugReportCallbackCreateInfoEXT* pCreateInfo,
                                      const VkAllocationCallbacks* pAllocator, VkDebugReportCallbackEXT* pCallback)
{
    auto func = (PFN_vkCreateDebugReportCallbackEXT) vkGetInstanceProcAddr(instance, "vkCreateDebugReportCallbackEXT");
    if (func != nullptr) {
        return func(instance, pCreateInfo, pAllocator, pCallback);
    } else {
        return VK_ERROR_EXTENSION_NOT_PRESENT;
    }
}

void DestroyDebugReportCallbackEXT(VkInstance instance, VkDebugReportCallbackEXT callback, const VkAllocationCallbacks* pAllocator)
{
    auto func = (PFN_vkDestroyDebugReportCallbackEXT) vkGetInstanceProcAddr(instance, "vkDestroyDebugReportCallbackEXT");
    if (func != nullptr) {
        func(instance, callback, pAllocator);
    }
}

static VKAPI_ATTR VkBool32 VKAPI_CALL debugCallback(VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
                                                    uint64_t obj, size_t location, int32_t code, const char* layerPrefix,
                                                    const char* msg, void* userData)
{
    std::cerr << "validation layer: " << msg << std::endl;
    return VK_FALSE;
}

TutorialVK::TutorialVK()
{
    initialized_ = false;
    androidAppCtx = nullptr;
}
TutorialVK::~TutorialVK()
{

}


void TutorialVK::initVulkan(android_app* app)
{
    androidAppCtx = app;
    if (!InitVulkan()) {
        LOGW("Vulkan is unavailable, install vulkan and re-start");
        return;
    }
    createInstance();
    setupDebugCallback();
    pickPhysicalDevice();
    createLogicalDevice();
    initialized_ = true;
}
void TutorialVK::deleteVulkan()
{
    initialized_ = false;
}

bool TutorialVK::isVulkanReady()
{
    return initialized_;
}

void TutorialVK::mainLoop()
{

}

void TutorialVK::createInstance()
{
    std::vector<const char *> instance_extensions;
    std::vector<const char *> device_extensions;
    instance_extensions.push_back("VK_KHR_surface");
    instance_extensions.push_back("VK_KHR_android_surface");
    if( enableValidationLayers ){
        instance_extensions.push_back(VK_EXT_DEBUG_REPORT_EXTENSION_NAME);
    }
    device_extensions.push_back("VK_KHR_swapchain");

    if( enableValidationLayers && !checkValidationLayerSupport() ){
//        throw std::runtime_error("validation layers requested, but not available!");
        LOGE("validation layers requested, but not available!");
    }

    VkApplicationInfo appInfo = {
            .sType = VK_STRUCTURE_TYPE_APPLICATION_INFO,
            .pApplicationName = "TutorialVK",
            .applicationVersion = VK_MAKE_VERSION(1, 0, 0),
            .pEngineName = "TutorialEngin",
            .engineVersion = VK_MAKE_VERSION(1, 0, 0),
            .apiVersion = VK_API_VERSION_1_0
    };

    VkInstanceCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO,
            .pApplicationInfo = &appInfo,
            .enabledExtensionCount = static_cast<uint32_t>(instance_extensions.size()),
            .ppEnabledExtensionNames = instance_extensions.data(),
            .enabledLayerCount = 0,
    };
//    if( enableValidationLayers ){
//        createInfo.enabledLayerCount = validationLayers.size();
//        createInfo.ppEnabledExtensionNames = validationLayers.data();
//    }else{
//        createInfo.enabledLayerCount = 0;
//    }

    if( vkCreateInstance(&createInfo, nullptr, instance.replace()) != VK_SUCCESS){
        throw std::runtime_error("failed to create instance!");
    }

}

void TutorialVK::setupDebugCallback()
{
    if( !enableValidationLayers)
        return;
    VkDebugReportCallbackCreateInfoEXT createInfo = {
            .sType = VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT,
            .flags = VK_DEBUG_REPORT_ERROR_BIT_EXT | VK_DEBUG_REPORT_WARNING_BIT_EXT,
            .pfnCallback = debugCallback,
    };
    if( CreateDebugReportCallbackEXT(instance, &createInfo, nullptr, callback.replace()) != VK_SUCCESS ){
        throw std::runtime_error("failed to set up debug callback!");
    }
}

bool TutorialVK::checkValidationLayerSupport()
{
    uint32_t layerCount;
    vkEnumerateInstanceLayerProperties(&layerCount, nullptr);

    std::vector<VkLayerProperties> availableLayers(layerCount);
    vkEnumerateInstanceLayerProperties(&layerCount, availableLayers.data());

    for (const char* layerName : validationLayers) {
        bool layerFound = false;
        for (const auto& layerProperties : availableLayers) {
            if (strcmp(layerName, layerProperties.layerName) == 0) {
                layerFound = true;
                break;
            }
        }
        if (!layerFound) {
            return false;
        }
    }
    return true;
}

void TutorialVK::pickPhysicalDevice()
{
    uint32_t deviceCount = 0;
    vkEnumeratePhysicalDevices(instance, &deviceCount, nullptr);

    if( deviceCount == 0 ){
//        throw std::runtime_error("failed to find GPUs with Vulkan support!");
        LOGE("failed to find GPUs with Vulkan support!");
    }
    std::vector<VkPhysicalDevice> devices(deviceCount);
    vkEnumeratePhysicalDevices(instance, &deviceCount, devices.data());
    for( const auto &device : devices){
        if( isDeviceSuitable(device))
        {
            physicalDevice = device;
            break;
        }
    }
}

bool TutorialVK::isDeviceSuitable(VkPhysicalDevice device)
{
    QueueFamilyIndices indices = findQueueFamilies(device);
    return indices.isComplete();
}

QueueFamilyIndices TutorialVK::findQueueFamilies(VkPhysicalDevice device)
{
    QueueFamilyIndices indices;
    uint32_t queueFamilyCount = 0;
    vkGetPhysicalDeviceQueueFamilyProperties(device, &queueFamilyCount, nullptr);

    std::vector<VkQueueFamilyProperties> queueFamilies(queueFamilyCount);
    vkGetPhysicalDeviceQueueFamilyProperties(device, &queueFamilyCount, queueFamilies.data());

    int i = 0;
    for( const auto &queueFamily : queueFamilies ){
        if( queueFamily.queueCount > 0 && queueFamily.queueFlags & VK_QUEUE_GRAPHICS_BIT ){
            indices.graphicsFamily = i;
        }
        if( indices.isComplete() ){
            break;
        }
        ++i;
    }
    return indices;
}

void TutorialVK::createLogicalDevice()
{
    QueueFamilyIndices indices = findQueueFamilies(physicalDevice);

    VkDeviceQueueCreateInfo queueCreateInfo = {
            .sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO,
            .queueFamilyIndex = (uint32_t)indices.graphicsFamily,
            .queueCount = 1,
    };

    float queuePriority = 1.0f;
    queueCreateInfo.pQueuePriorities = &queuePriority;

    VkPhysicalDeviceFeatures deviceFeatures = {};

    VkDeviceCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO,
            .pQueueCreateInfos = &queueCreateInfo,
            .queueCreateInfoCount = 1,
            .pEnabledFeatures = &deviceFeatures,
            .enabledExtensionCount = 0
    };

    if (enableValidationLayers) {
        createInfo.enabledLayerCount = validationLayers.size();
        createInfo.ppEnabledLayerNames = validationLayers.data();
    } else {
        createInfo.enabledLayerCount = 0;
    }

    if (vkCreateDevice(physicalDevice, &createInfo, nullptr, device.replace()) != VK_SUCCESS) {
//        throw std::runtime_error("failed to create logical device!");
        LOGE("failed to create logical device!");
    }

    vkGetDeviceQueue(device, indices.graphicsFamily, 0, &graphicsQueue);
}