from itertools import izip

from django import forms
from django import template
from django.conf import settings


@register.filter
def is_checkbox(field):
    return isinstance(field.field.widget, forms.CheckboxInput)
