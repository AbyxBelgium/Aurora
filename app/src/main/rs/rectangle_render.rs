#pragma version(1)
#pragma rs java_package_name(be.abyx.aurora)
// We don't need very high precision floating points
#pragma rs_fp_relaxed

// Destination colour of the background can be set here.
float destinationR;
float destinationG;
float destinationB;
float destinationA;

uchar4 RS_KERNEL rectangleRender(uchar4 in, uint32_t x, uint32_t y) {
    //Convert input uchar4 to float4
    float4 f4 = rsUnpackColor8888(in);

    if (f4.a == 0) {
        return rsPackColorTo8888(destinationR, destinationG, destinationB, destinationA);
    }

    return rsPackColorTo8888(f4);
}