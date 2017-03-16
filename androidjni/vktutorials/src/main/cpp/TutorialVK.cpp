#include "TutorialVK.h"
#include "vector"
#include "my_log.h"



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
    device_extensions.push_back("VK_KHR_swapchain");

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

    if( vkCreateInstance(&createInfo, nullptr, instance.replace()) != VK_SUCCESS){
        throw std::runtime_error("failed to create instance!");
    }



}