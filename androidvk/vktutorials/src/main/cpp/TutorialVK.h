#ifndef __TutorialVK_H__
#define __TutorialVK_H__
#include <android_native_app_glue.h>
#include "vulkan_wrapper.h"
#define GLM_FORCE_RADIANS
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtx/hash.hpp>

#include <iostream>
#include <stdexcept>
#include <functional>
#include <vector>
//#include <hash_fun.h>

template <typename T>
class VDeleter {
public:
    VDeleter() : VDeleter([](T, VkAllocationCallbacks*) {}) {}

    VDeleter(std::function<void(T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [=](T obj)
        {
            deletef(obj, nullptr);
        };
    }

    VDeleter(const VDeleter<VkInstance>& instance, std::function<void(VkInstance, T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [&instance, deletef](T obj)
        {
            deletef(instance, obj, nullptr);
        };
    }

    VDeleter(const VDeleter<VkDevice>& device, std::function<void(VkDevice, T, VkAllocationCallbacks*)> deletef) {
        this->deleter = [&device, deletef](T obj)
        {
            deletef(device, obj, nullptr);
        };
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

    void cleanup() {
        if (object != VK_NULL_HANDLE) {
            deleter(object);
        }
        object = VK_NULL_HANDLE;
    }

private:
    T object{VK_NULL_HANDLE};
    std::function<void(T)> deleter;

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
    glm::vec3 pos;
    glm::vec3 color;
    glm::vec2 texCoord;

    static VkVertexInputBindingDescription getBindingDescription(){
        VkVertexInputBindingDescription bindingDescription = {
                .binding = 0,
                .stride = sizeof(Vertex),
                .inputRate = VK_VERTEX_INPUT_RATE_VERTEX,
        };
        return bindingDescription;
    }

    static std::array<VkVertexInputAttributeDescription, 3> getAttributeDescriptions(){
        std::array<VkVertexInputAttributeDescription, 3> attributeDescriptions = {};

        attributeDescriptions[0].binding = 0;
        attributeDescriptions[0].location = 0;
        attributeDescriptions[0].format = VK_FORMAT_R32G32B32_SFLOAT;
        attributeDescriptions[0].offset = offsetof(Vertex, pos);

        attributeDescriptions[1].binding = 0;
        attributeDescriptions[1].location = 1;
        attributeDescriptions[1].format = VK_FORMAT_R32G32B32_SFLOAT;
        attributeDescriptions[1].offset = offsetof(Vertex, color);

        attributeDescriptions[2].binding = 0;
        attributeDescriptions[2].location = 2;
        attributeDescriptions[2].format = VK_FORMAT_R32G32_SFLOAT;
        attributeDescriptions[2].offset = offsetof(Vertex, texCoord);

        return attributeDescriptions;
    };

    bool operator==(const Vertex& other) const {
//        return true;
        return pos == other.pos && color == other.color && texCoord == other.texCoord;
    }

};


namespace std {
    template<> struct hash<Vertex> {
        size_t operator()(Vertex const& vertex) const {
//            std::hash<glm::vec3> hv3;
//            std::hash<glm::vec2> hv2;
//            size_t re = (( hv3(vertex.pos) ^
//                          (hv3(vertex.color) << 1)) >> 1) ^
//                        (hv2(vertex.texCoord) << 1);
//            return re;

            return ((hash<glm::vec3>()(vertex.pos) ^
                     (hash<glm::vec3>()(vertex.color) << 1)) >> 1) ^
                   (hash<glm::vec2>()(vertex.texCoord) << 1);
        }
    };
}

struct UniformBufferObject {
    glm::mat4 model;
    glm::mat4 view;
    glm::mat4 proj;
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
    void updateUniformBuffer();
    void drawFrame();
    void recreateSwapChain();

private:
    android_app* androidAppCtx;
    bool initialized_;
    VDeleter<VkInstance> instance{DestroyInstance};
    VDeleter<VkDebugReportCallbackEXT>callback{instance, DestroyDebugReportCallbackEXT };
    VDeleter<VkSurfaceKHR> surface{instance, DestroySurfaceKHR};

    VkPhysicalDevice  physicalDevice = VK_NULL_HANDLE;
    VDeleter<VkDevice> device{DestroyDevice};
    VkQueue graphicsQueue;
    VkQueue presentQueue;

    VDeleter<VkSwapchainKHR> swapChain{device, DestroySwapchainKHR};
    std::vector<VkImage> swapChainImages;
    VkFormat swapChainImageFormat;
    VkExtent2D swapChainExtent;
    std::vector<VDeleter<VkImageView>> swapChainImageViews;
    std::vector<VDeleter<VkFramebuffer> > swapChainFramebuffers;

    VDeleter<VkRenderPass> renderPass{device, DestroyRenderPass};
    VDeleter<VkDescriptorSetLayout> descriptorSetLayout{device, DestroyDescriptorSetLayout};
    VDeleter<VkPipelineLayout> pipelineLayout{device, DestroyPipelineLayout};
    VDeleter<VkPipeline> graphicsPipeline{device, DestroyPipeline};

    VDeleter<VkCommandPool> commandPool{device, DestroyCommandPool};

    VDeleter<VkImage> depthImage{device, DestroyImage};
    VDeleter<VkDeviceMemory> depthImageMemory{device, FreeMemory};
    VDeleter<VkImageView> depthImageView{device, DestroyImageView};

    VDeleter<VkImage> textureImage{device, DestroyImage};
    VDeleter<VkDeviceMemory> textureImageMemory{device, FreeMemory};
    VDeleter<VkImageView> textureImageView{device, DestroyImageView};
    VDeleter<VkSampler> textureSampler{device, DestroySampler};

//    std::vector<Vertex> vertices;
//    std::vector<uint32_t> indices;
    VDeleter<VkBuffer> vertexBuffer{device, DestroyBuffer};
    VDeleter<VkDeviceMemory> vertexBufferMemory{device, FreeMemory };
    VDeleter<VkBuffer> indexBuffer{device, DestroyBuffer};
    VDeleter<VkDeviceMemory> indexBufferMemory{device, FreeMemory};

    VDeleter<VkBuffer> uniformStagingBuffer{device, DestroyBuffer};
    VDeleter<VkDeviceMemory> uniformStagingBufferMemory{ device, FreeMemory};
    VDeleter<VkBuffer> uniformBuffer{device, DestroyBuffer};
    VDeleter<VkDeviceMemory> uniformBufferMemory{device, FreeMemory};

    VDeleter<VkDescriptorPool> descriptorPool{device, DestroyDescriptorPool};
    VkDescriptorSet descriptorSet;

    std::vector<VkCommandBuffer> commandBuffers;

    VDeleter<VkSemaphore> imageAvailableSemaphore{device, DestroySemaphore};
    VDeleter<VkSemaphore> renderFinishedSemaphore{device, DestroySemaphore};


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
    uint32_t findMemoryType(uint32_t typeFilter, VkMemoryPropertyFlags properties);
    void createShaderModule(const std::vector<char>& code, VDeleter<VkShaderModule>& shaderModule);
    void createBuffer(VkDeviceSize size, VkBufferUsageFlags usage, VkMemoryPropertyFlags properties, VDeleter<VkBuffer> &buffer, VDeleter<VkDeviceMemory> &bufferMemory);
    void copyBuffer(VkBuffer srcBuffer, VkBuffer dstBuffer, VkDeviceSize size);
    void createImage(uint32_t width, uint32_t height, VkFormat format, VkImageTiling tiling, VkImageUsageFlags usage, VkMemoryPropertyFlags properties, VDeleter<VkImage>& image, VDeleter<VkDeviceMemory>& imageMemory);
    void transitionImageLayout(VkImage image, VkFormat format, VkImageLayout oldLayout, VkImageLayout newLayout);
    void copyImage(VkImage srcImage, VkImage dstImage, uint32_t width, uint32_t height);
    void createImageView(VkImage image, VkFormat format, VkImageAspectFlags aspectFlags, VDeleter<VkImageView> &imageView);
    VkFormat findDepthFormat();
    VkFormat findSupportedFormat(const std::vector<VkFormat>& candidates, VkImageTiling tiling, VkFormatFeatureFlags features);
    bool hasStencilComponent(VkFormat format);
    VkCommandBuffer beginSingleTimeCommands();
    void endSingleTimeCommands(VkCommandBuffer commandBuffer);
    void  createLogicalDevice();
    void createSwapChain();
    void createImageViews();
    void createRenderPass();
    void createDescriptorSetLayout();
    void createGraphicsPipeline();
    void createFramebuffers();
    void createCommandPool();
    void createDepthResources();
    void createTextureImage();
    void createTextureImageView();
    void createTextureSampler();
    void loadModel();
    void createVertexBuffer();
    void createIndexBuffer();
    void createUniformBuffer();
    void createDescriptorPool();
    void createDescriptorSet();
    void createCommandBuffers();
    void createSemaphores();

    void createVertexBuffer_1();
};

#endif

