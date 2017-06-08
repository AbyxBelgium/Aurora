#pragma version(1)
#pragma rs java_package_name(be.abyx.aurora)
// We don't need very high precision floating points
#pragma rs_fp_relaxed

// Center position of the circle
int centerX = 0;
int centerY = 0;

// Radius of the circle
int radius = 0;

// Destination colour of the background can be set here.
int destinationR;
int destinationG;
int destinationB;
int destinationA;

static int square(int input) {
    return input * input;
}

uchar4 RS_KERNEL circleRender(uchar4 in, uint32_t x, uint32_t y) {
    //Convert input uchar4 to float4
    float4 f4 = rsUnpackColor8888(in);

    // Check if the current coordinates fall inside the circle
    if (square(x - centerX) + square(y - centerY) < square(radius)) {
        // Check if current position is transparent we then need to add the background!)
        if (f4.a == 0) {
            f4.r = destinationR;
            f4.g = destinationG;
            f4.b = destinationB;
            f4.a = destinationA;
        }
    }

    return rsPackColorTo8888(f4);
}