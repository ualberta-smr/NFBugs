from django.conf import settings
from django.forms.formsets import BaseFormSet
from django.template import Context
from django.template.loader import get_template
from django import template

from crispy_forms.helper import FormHelper

register = template.Library()
# We import the filters, so they are available when doing load crispy_forms_tags
from crispy_forms_filters import *

class BasicNode(template.Node):

    def get_render(self, context):
    
        # ...
    
        node_context = context.__copy__()
        
        # ...
