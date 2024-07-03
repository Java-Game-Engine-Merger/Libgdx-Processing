import os
from PIL import Image

def is_transparent(pixel):
    """检查像素是否完全透明"""
    return pixel[3] == 0

def find_non_transparent_bounds(image):
    """找到图像中非透明部分的边界"""
    width, height = image.size
    pixels = image.load()

    left, right, top, bottom = width, 0, height, 0

    # 遍历每一行像素，计算bottom
    for y in range(height):
        for x in range(width):
            if not is_transparent(pixels[x, y]):
                if y < top:
                    top = y
                if y > bottom:
                    bottom = y

    # 遍历每一列像素，计算right
    for x in range(width):
        for y in range(height):
            if not is_transparent(pixels[x, y]):
                if x < left:
                    left = x
                if x > right:
                    right = x

    return left, right, top, bottom

def crop_image(image):
    """裁剪图像，去除完全透明的部分并保留20px的边缘"""
    left, right, top, bottom = find_non_transparent_bounds(image)

    # 保留20px的边缘
    left = max(0, left - 20)
    right = min(image.width, right + 20)
    top = max(0, top - 20)
    bottom = min(image.height, bottom + 20)

    return image.crop((left, top, right, bottom))

def process_image(file_path):
    """处理图像并保存裁剪后的结果"""
    image = Image.open(file_path).convert("RGBA")
    cropped_image = crop_image(image)
    cropped_image.save(file_path, "WEBP")
    print(f"Processed image saved to {file_path}")

def process_directory(directory):
    """遍历目录及其子目录中的所有WebP文件并处理它们"""
    for root, _, files in os.walk(directory):
        for file in files:
            if file.lower().endswith(".webp"):
                file_path = os.path.join(root, file)
                process_image(file_path)

if __name__ == "__main__":
    directory = "../assets"  # 替换为你的目录路径
    process_directory(directory)
    print("All WebP images processed.")