import json

from excelReader import ExcelReader
from exercise import ExerciseEntity
from jsonEncoder import JsonEncoder
from muscle import MuscleEntity


outputPath = '../app/src/main/assets/'
reader = ExcelReader('../data/db-preload.xlsx')


muscles = [MuscleEntity(muscle) for muscle in reader.muscles]
exercises = [ExerciseEntity(exercise) for exercise in reader.exercises]




with open(outputPath + 'muscles.json', 'w') as file:
    json.dump(muscles, file, cls=JsonEncoder, indent=2)
with open(outputPath + 'exercises.json', 'w') as file:
    json.dump(exercises, file, cls=JsonEncoder, indent=2)

