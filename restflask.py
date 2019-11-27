from flask import Flask
from rasa_nlu.training_data  import load_data
from rasa_nlu.config import RasaNLUModelConfig
from rasa_nlu.model import Trainer
from rasa_nlu import config
from rasa_nlu.model import Metadata, Interpreter
import spacy



app = Flask(__name__)
interpreter = Interpreter.load('/home/sandeepa/My Data/Temp/Intent Classification With Rasa - Spacy/models/default/new')
nlp = spacy.load('en')

@app.route('/getintent/<text>')
def getintent(text):
    intentname=interpreter.parse(text)
    return intentname['intent']['name']

@app.route('/getname/<text>')
def getName(text):
    docx = nlp(text)
    for word in docx.ents:
        if(word.label_=='PERSON'):
            return word.text
    return 'Name not found'

    

@app.route('/')
def hello():
    return "hello"

if __name__ == '__main__':
    app.run()
