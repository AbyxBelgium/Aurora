#pragma version(1)
#pragma rs java_package_name(be.abyx.aurora)
// We don't need very high precision floating points
#pragma rs_fp_relaxed

// The value by which the hue must be shifted
float shift = 0.0;

// Convert a pixel in RGB color space to the HSV color space
static float3 convertRgbToHsv(float red, float green, float blue) {
    float3 hsv;

    float M = max(max(red, green), blue);
    float m = min(min(red, green), blue);

    hsv[2] = M;

    if (M == 0) {
        hsv[1] = 0;
    } else {
        hsv[1] = (M - m) / M;
    }

    if (hsv[1] == 0) {
        hsv[0] = 0;
    } else if (M == red && green >= blue) {
        hsv[0] = 60 * (green - blue) / (M - m);
    } else  if (M == red && green < blue) {
        hsv[0] = 60 * (green - blue) / (M - m) + 360;
    } else if (M == green) {
        hsv[0] = 60 * (blue - red) / (M - m) + 120;
    } else {
        hsv[0] = 60 * (red - green) / (M - m) + 240;
    }

    return hsv;
}

// Convert a pixel in the HSV color space to RGB. Alpha value of the RGB output is always set to 255
static float4 convertHsvToRgb(float h, float s, float v) {
    float4 rgb;

    // Set alpha value always to maximum
    rgb[3] = 255;

    float c = v * s;

    float hNew = h / 60;
    float x = c * (1 - fabs(fmod(hNew, 2) - 1));

    if (hNew <= 1) {
        rgb[0] = c;
        rgb[1] = x;
        rgb[2] = 0;
    } else if (hNew <= 2) {
        rgb[0] = x;
        rgb[1] = c;
        rgb[2] = 0;
    } else if (hNew <= 3) {
        rgb[0] = 0;
        rgb[1] = c;
        rgb[2] = x;
    } else if (hNew <= 4) {
        rgb[0] = 0;
        rgb[1] = x;
        rgb[2] = c;
    } else if (hNew <= 5) {
        rgb[0] = x;
        rgb[1] = 0;
        rgb[2] = c;
    } else if (hNew <= 6) {
        rgb[0] = c;
        rgb[1] = 0;
        rgb[2] = x;
    }

    float m = v - c;
    rgb[0] = rgb[0] + m;
    rgb[1] = rgb[1] + m;
    rgb[2] = rgb[2] + m;

    return rgb;
}

uchar4 RS_KERNEL hueShift(uchar4 in, uint32_t x, uint32_t y) {
    //Convert input uchar4 to float4
    float4 f4 = rsUnpackColor8888(in);

    float3 hsv = convertRgbToHsv(f4.r, f4.g, f4.b);

    hsv[0] = fmod(hsv[0] + shift, 360);

    float4 rgb = convertHsvToRgb(hsv[0], hsv[1], hsv[2]);

    //Put the values in the output uchar4, note that we keep the alpha value
    return rsPackColorTo8888(rgb[0], rgb[1], rgb[2], rgb[3]);
}