#pragma version(1)
#pragma rs java_package_name(be.abyx.aurora)
#pragma rs_fp_relaxed

// Center position of the circle
int centerX = 0;
int centerY = 0;

// Radius of the circle
int radius = 0;

static int square(int input) {
    return input * input;
}

uchar4 RS_KERNEL circleRender(uchar4 in, uchar4 background, uint32_t x, uint32_t y) {
    //Convert input uchar4 to float4
    float4 f4 = rsUnpackColor8888(in);

    // Check if the current coordinates fall inside the circle
    if (square(x - centerX) + square(y - centerY) < square(radius)) {
        // Check if current position is transparent we then need to add the background!)
        if (f4.a == 0) {
            return background;
        }
    }

    return rsPackColorTo8888(f4);
}