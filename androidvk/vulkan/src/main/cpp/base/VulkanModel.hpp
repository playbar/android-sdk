/*
* Vulkan Model loader using ASSIMP
*
* Copyright(C) 2016-2017 by Sascha Willems - www.saschawillems.de
*
* This code is licensed under the MIT license(MIT) (http://opensource.org/licenses/MIT)
*/

#pragma once

#include <stdlib.h>
#include <string>
#include <fstream>
#include <vector>

#include "vulkan/vulkan.h"

#include <assimp/Importer.hpp> 
#include <assimp/scene.h>     
#include <assimp/postprocess.h>
#include <assimp/cimport.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "VulkanDevice.hpp"
#include "VulkanBuffer.hpp"

#if defined(__ANDROID__)
#include <android/asset_manager.h>
#endif

namespace vks
{
	/** @brief Vertex layout components */
	typedef enum Component {
		VERTEX_COMPONENT_POSITION = 0x0,
		VERTEX_COMPONENT_NORMAL = 0x1,
		VERTEX_COMPONENT_COLOR = 0x2,
		VERTEX_COMPONENT_UV = 0x3,
		VERTEX_COMPONENT_TANGENT = 0x4,
		VERTEX_COMPONENT_BITANGENT = 0x5,
		VERTEX_COMPONENT_DUMMY_FLOAT = 0x6,
		VERTEX_COMPONENT_DUMMY_VEC4 = 0x7
	} Component;

	/** @brief Stores vertex layout components for model loading and Vulkan vertex input and atribute bindings  */
	struct VertexLayout
	{
	public:
		/** @brief Components used to generate vertices from */
		std::vector<Component> components;
		VertexLayout(std::vector<Component> components);
		uint32_t stride();
	};

	/** @brief Used to parametrize model loading */
	struct ModelCreateInfo
	{
		glm::vec3 center;
		glm::vec3 scale;
		glm::vec2 uvscale;

		ModelCreateInfo();
		ModelCreateInfo(glm::vec3 scale, glm::vec2 uvscale, glm::vec3 center);
		ModelCreateInfo(float scale, float uvscale, float center);

	};

	struct Model {
		VkDevice device = nullptr;
		vks::Buffer vertices;
		vks::Buffer indices;
		uint32_t indexCount = 0;
		uint32_t vertexCount = 0;

		/** @brief Stores vertex and index base and counts for each part of a model */
		struct ModelPart {
			uint32_t vertexBase;
			uint32_t vertexCount;
			uint32_t indexBase;
			uint32_t indexCount;
		};
		std::vector<ModelPart> parts;

		static const int defaultFlags = aiProcess_FlipWindingOrder | aiProcess_Triangulate | aiProcess_PreTransformVertices | aiProcess_CalcTangentSpace | aiProcess_GenSmoothNormals;

		struct Dimension
		{
			glm::vec3 min = glm::vec3(FLT_MAX);
			glm::vec3 max = glm::vec3(-FLT_MAX);
			glm::vec3 size;
		} dim;

		/** @brief Release all Vulkan resources of this model */
		void destroy();

		/**
		* Loads a 3D model from a file into Vulkan buffers
		*
		* @param device Pointer to the Vulkan device used to generated the vertex and index buffers on
		* @param filename File to load (must be a model format supported by ASSIMP)
		* @param layout Vertex layout components (position, normals, tangents, etc.)
		* @param createInfo MeshCreateInfo structure for load time settings like scale, center, etc.
		* @param copyQueue Queue used for the memory staging copy commands (must support transfer)
		* @param (Optional) flags ASSIMP model loading flags
		*/
		bool loadFromFile(const std::string& filename, vks::VertexLayout layout, vks::ModelCreateInfo *createInfo,
						  vks::VulkanDevice *device, VkQueue copyQueue, const int flags = defaultFlags);

		/**
		* Loads a 3D model from a file into Vulkan buffers
		*
		* @param device Pointer to the Vulkan device used to generated the vertex and index buffers on
		* @param filename File to load (must be a model format supported by ASSIMP)
		* @param layout Vertex layout components (position, normals, tangents, etc.)
		* @param scale Load time scene scale
		* @param copyQueue Queue used for the memory staging copy commands (must support transfer)
		* @param (Optional) flags ASSIMP model loading flags
		*/
		bool loadFromFile(const std::string& filename, vks::VertexLayout layout, float scale, vks::VulkanDevice *device,
						  VkQueue copyQueue, const int flags = defaultFlags);
	};
};