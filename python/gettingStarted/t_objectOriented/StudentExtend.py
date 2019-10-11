#!/usr/bin/env python
# -*- coding: utf-8 -*-
from t_objectOriented.Student import Student


class StudentExtend(Student):
    def __init__(self, name, score):
        self.__name = name + " extend"
        self.__score = score + 10
        super(StudentExtend, self).__init__(self.__name, self.__score)




