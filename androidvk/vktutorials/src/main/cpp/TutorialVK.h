#ifndef __TutorialVK_H__
#define __TutorialVK_H__
#include <android_native_app_glue.h>
#include "vulkan_wrapper.h"
#include "glm/glm.hpp"
#include <iostream>
#include <stdexcept>
#include <functional>
#include <vector>

template <typename T>
class VDeleter {
public:
    VDeleter() : VDeleter([](T, VkAllocationCallbacks*) {}) {}

    VDeleter(std::function<void(T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [=](T obj) { deletef(obj, nullptr); };
    }

    VDeleter(const VDeleter<VkInstance>& instance, std::function<void(VkInstance, T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [&instance, deletef](T obj) { deletef(instance, obj, nullptr); };
    }

    VDeleter(const VDeleter<VkDevice>& device, std::function<void(VkDevice, T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [&device, deletef](T obj) { deletef(device, obj, nullptr); };
    }

    ~VDeleter() {
        cleanup();
    }

    const T* operator &() const {
        return &object;
    }

    T* replace() {
        cleanup();
        return &object;
    }

    operator T() const {
        return object;
    }

    void operator=(T rhs) {
        if (rhs != object) {
            cleanup();
            object = rhs;
        }
    }

    template<typename V>
    bool operator==(V rhs) {
        return object == T(rhs);
    }

private:
    T object{VK_NULL_HANDLE};
    std::function<void(T)> deleter;

    void cleanup() {
        if (object != VK_NULL_HANDLE) {
            deleter(object);
        }
        object = VK_NULL_HANDLE;
    }
};

struct QueueFamilyIndices {
    int graphicsFamily = -1;
    int presentFamily = -1;
    bool isComplete() {
        return graphicsFamily >= 0 && presentFamily >=0;
    }
};

struct SwapChainSupportDetails{
    VkSurfaceCapabilitiesKHR capabilities;
    std::vector<VkSurfaceFormatKHR> formats;
    std::vector<VkPresentModeKHR> presentModes;
};

VkResult CreateDebugReportCallbackEXT(VkInstance instance, const VkDebugReportCallbackCreateInfoEXT* pCreateInfo,
                                      const VkAllocationCallbacks* pAllocator, VkDebugReportCallbackEXT* pCallback);
void DestroyDebugReportCallbackEXT(VkInstance instance, VkDebugReportCallbackEXT callback, const VkAllocationCallbacks* pAllocator);

struct Vertex
{
    glm::vec2 pos;
    glm::vec3 color;

    static VkVertexInputBindingDescription getBindingDescription(){
        VkVertexInputBindingDescription bindingDescription = {
                .binding = 0,
                .stride = sizeof(Vertex),
                .inputRate = VK_VERTEX_INPUT_RATE_VERTEX,
        };
        return bindingDescription;
    }

    static std::array<VkVertexInputAttributeDescription, 2> getAttributeDescriptions(){
        std::array<VkVertexInputAttributeDescription, 2> attributeDescriptions = {};

        attributeDescriptions[0].binding = 0;
        attributeDescriptions[0].location = 0;
        attributeDescriptions[0].format = VK_FORMAT_R32G32_SFLOAT;
        attributeDescriptions[0].offset = offsetof(Vertex, pos);

        attributeDescriptions[1].binding = 0;
        attributeDescriptions[1].location = 1;
        attributeDescriptions[1].format = VK_FORMAT_R32G32B32_SFLOAT;
        attributeDescriptions[1].offset = offsetof(Vertex, color);

        return attributeDescriptions;
    };

};

class TutorialVK
{
public:
    TutorialVK();
    ~TutorialVK();

public:
    void initVulkan(android_app* app);
    void deleteVulkan();
    bool isVulkanReady();
    void drawFrame();
    void recreateSwapChain();

private:
    android_app* androidAppCtx;
    bool initialized_;
    VDeleter<VkInstance> instance{vkDestroyInstance};
    VDeleter<VkDebugReportCallbackEXT>callback{instance, DestroyDebugReportCallbackEXT };
    VDeleter<VkSurfaceKHR> surface{instance, vkDestroySurfaceKHR};

    VkPhysicalDevice  physicalDevice = VK_NULL_HANDLE;
    VDeleter<VkDevice> device{vkDestroyDevice};
    VkQueue graphicsQueue;
    VkQueue presentQueue;

    VDeleter<VkSwapchainKHR> swapChain{device, vkDestroySwapchainKHR};
    std::vector<VkImage> swapChainImages;
    VkFormat swapChainImageFormat;
    VkExtent2D swapChainExtent;
    std::vector<VDeleter<VkImageView>> swapChainImageViews;
    std::vector<VDeleter<VkFramebuffer> > swapChainFramebuffers;

    VDeleter<VkRenderPass> renderPass{device, vkDestroyRenderPass};
    VDeleter<VkPipelineLayout> pipelineLayout{device, vkDestroyPipelineLayout};
    VDeleter<VkPipeline> graphicsPipeline{device, vkDestroyPipeline};

    VDeleter<VkCommandPool> commandPool{device, vkDestroyCommandPool};
    std::vector<VkCommandBuffer> commandBuffers;

    VDeleter<VkSemaphore> imageAvailableSemaphore{device, vkDestroySemaphore};
    VDeleter<VkSemaphore> renderFinishedSemaphore{device, vkDestroySemaphore};


private:
    void createInstance();
    void setupDebugCallback();
    void createSurface();
    bool checkValidationLayerSupport();
    bool checkDeviceExtensionSupport(VkPhysicalDevice device);
    void pickPhysicalDevice();
    bool isDeviceSuitable(VkPhysicalDevice device);
    QueueFamilyIndices findQueueFamilies(VkPhysicalDevice device);
    SwapChainSupportDetails querySwapChainSupport(VkPhysicalDevice device);
    VkSurfaceFormatKHR chooseSwapSurfaceFormat(const std::vector<VkSurfaceFormatKHR> &availableFormats);
    VkPresentModeKHR chooseSwapPresentMode(const std::vector<VkPresentModeKHR>availablePresentModes);
    VkExtent2D chooseSwapExtent(const VkSurfaceCapabilitiesKHR & capabilities);
    std::vector<char> readFile(const char* filePathe);
    void createShaderModule(const std::vector<char>& code, VDeleter<VkShaderModule>& shaderModule);
    void  createLogicalDevice();
    void createSwapChain();
    void createImageViews();
    void createRenderPass();
    void createGraphicsPipeline();
    void createFramebuffers();
    void createCommandPool();
    void createCommandBuffers();
    void createSemaphores();
};

#endif

