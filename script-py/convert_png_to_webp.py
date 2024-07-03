import os
from PIL import Image

def convert_png_to_webp(input_folder):
    # 遍历输入文件夹及其子文件夹中的所有文件
    for root, _, files in os.walk(input_folder):
        for filename in files:
            if filename.lower().endswith('.png'):
                # 构建输入和输出文件的完整路径
                input_path = os.path.join(root, filename)
                output_path = os.path.join(root, os.path.splitext(filename)[0] + '.webp')

                # 打开PNG文件并转换为WebP格式
                with Image.open(input_path) as img:
                    img.save(output_path, 'webp', lossless=True)
                    print(f"Converted {input_path} to {output_path}")

                # 删除原始的PNG文件
                os.remove(input_path)
                print(f"Deleted original PNG file: {input_path}")

if __name__ == "__main__":
    input_folder = '../assets/font/mapleMono'  # 替换为你的PNG文件夹路径

    convert_png_to_webp(input_folder)