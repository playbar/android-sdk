#include "TutorialVK.h"

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