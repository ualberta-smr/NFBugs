from flask import Blueprint, render_template
from randomchat.settings import settings

import socketio
import random
import logging

def free_space(pairs):
	pairs = [pair for pair in pairs if pair != ['EMPTY', 'EMPTY']]
