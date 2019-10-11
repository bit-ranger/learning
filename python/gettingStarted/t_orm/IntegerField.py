#!/usr/bin/env python
# -*- coding: utf-8 -*-
from t_orm import Field


class IntegerField(Field):
    def __init__(self, name):
        super(IntegerField, self).__init__(name, 'bigint')