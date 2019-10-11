#!/usr/bin/env python
# -*- coding: utf-8 -*-
import Image

im = Image.open('BingImageOfTheDay.jpg')
# 获得图像尺寸:
w, h = im.size
# 缩放到50%:
im.thumbnail((w//2, h//2))
# 把缩放后的图像用jpeg格式保存:
im.save('thumbnail.jpg', 'jpeg')