#!/usr/bin/env python
# -*- coding: utf-8 -*-
from t_orm import Field


class StringField(Field):
    def __init__(self, name):
        super(StringField, self).__init__(name, 'varchar(100)')