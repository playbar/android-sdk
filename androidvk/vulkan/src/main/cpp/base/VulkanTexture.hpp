
#pragma once
#include <stdlib.h>
#include <string>
#include <fstream>
#include <vector>

#include "vulkan/vulkan.h"

#include <gli/gli.hpp>

#include "VulkanTools.h"
#include "VulkanDevice.hpp"
#include "VulkanBuffer.hpp"

#if defined(__ANDROID__)
#include <android/asset_manager.h>
#endif

namespace vks
{
	/** @brief Vulkan texture base class */
	class Texture {
	public:
		vks::VulkanDevice *device;
		VkImage image;
		VkImageLayout imageLayout;
		VkDeviceMemory deviceMemory;
		VkImageView view;
		uint32_t width, height;
		uint32_t mipLevels;
		uint32_t layerCount;
		VkDescriptorImageInfo descriptor;

		/** @brief Optional sampler to use with this texture */
		VkSampler sampler;

		/** @brief Update image descriptor from current sampler, view and image layout */
		void updateDescriptor();

		/** @brief Release all Vulkan resources held by this texture */
		void destroy();
	};

	/** @brief 2D texture */
	class Texture2D : public Texture {
	public:
		/**
		* Load a 2D texture including all mip levels
		*
		* @param filename File to load (supports .ktx and .dds)
		* @param format Vulkan format of the image data stored in the file
		* @param device Vulkan device to create the texture on
		* @param copyQueue Queue used for the texture staging copy commands (must support transfer)
		* @param (Optional) imageUsageFlags Usage flags for the texture's image (defaults to VK_IMAGE_USAGE_SAMPLED_BIT)
		* @param (Optional) imageLayout Usage layout for the texture (defaults VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
		* @param (Optional) forceLinear Force linear tiling (not advised, defaults to false)
		*
		*/
		void loadFromFile(
			std::string filename, 
			VkFormat format,
			vks::VulkanDevice *device,
			VkQueue copyQueue,
			VkImageUsageFlags imageUsageFlags = VK_IMAGE_USAGE_SAMPLED_BIT,
			VkImageLayout imageLayout = VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL, 
			bool forceLinear = false);

		/**
		* Creates a 2D texture from a buffer
		*
		* @param buffer Buffer containing texture data to upload
		* @param bufferSize Size of the buffer in machine units
		* @param width Width of the texture to create
		* @param height Height of the texture to create
		* @param format Vulkan format of the image data stored in the file
		* @param device Vulkan device to create the texture on
		* @param copyQueue Queue used for the texture staging copy commands (must support transfer)
		* @param (Optional) filter Texture filtering for the sampler (defaults to VK_FILTER_LINEAR)
		* @param (Optional) imageUsageFlags Usage flags for the texture's image (defaults to VK_IMAGE_USAGE_SAMPLED_BIT)
		* @param (Optional) imageLayout Usage layout for the texture (defaults VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
		*/
		void fromBuffer(
			void* buffer,
			VkDeviceSize bufferSize,
			VkFormat format,
			uint32_t width,
			uint32_t height,
			vks::VulkanDevice *device,
			VkQueue copyQueue,
			VkFilter filter = VK_FILTER_LINEAR,
			VkImageUsageFlags imageUsageFlags = VK_IMAGE_USAGE_SAMPLED_BIT,
			VkImageLayout imageLayout = VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);

	};

	/** @brief 2D array texture */
	class Texture2DArray : public Texture {
	public:
		/**
		* Load a 2D texture array including all mip levels
		*
		* @param filename File to load (supports .ktx and .dds)
		* @param format Vulkan format of the image data stored in the file
		* @param device Vulkan device to create the texture on
		* @param copyQueue Queue used for the texture staging copy commands (must support transfer)
		* @param (Optional) imageUsageFlags Usage flags for the texture's image (defaults to VK_IMAGE_USAGE_SAMPLED_BIT)
		* @param (Optional) imageLayout Usage layout for the texture (defaults VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
		*
		*/
		void loadFromFile(
			std::string filename,
			VkFormat format,
			vks::VulkanDevice *device,
			VkQueue copyQueue,
			VkImageUsageFlags imageUsageFlags = VK_IMAGE_USAGE_SAMPLED_BIT,
			VkImageLayout imageLayout = VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);

	};

	/** @brief Cube map texture */
	class TextureCubeMap : public Texture {
	public:
		/**
		* Load a cubemap texture including all mip levels from a single file
		*
		* @param filename File to load (supports .ktx and .dds)
		* @param format Vulkan format of the image data stored in the file
		* @param device Vulkan device to create the texture on
		* @param copyQueue Queue used for the texture staging copy commands (must support transfer)
		* @param (Optional) imageUsageFlags Usage flags for the texture's image (defaults to VK_IMAGE_USAGE_SAMPLED_BIT)
		* @param (Optional) imageLayout Usage layout for the texture (defaults VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
		*
		*/
		void loadFromFile(
			std::string filename,
			VkFormat format,
			vks::VulkanDevice *device,
			VkQueue copyQueue,
			VkImageUsageFlags imageUsageFlags = VK_IMAGE_USAGE_SAMPLED_BIT,
			VkImageLayout imageLayout = VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL);
	};

}