import pandas as pd

from exercise import Exercise
from muscle import Muscle


class ExcelReader:
    def __init__(self, path):
        self.path = path
        self._readData()
        self._checkExerciseMuscles()

    def _readData(self):
        self.muscles = [Muscle(line) for line in self._readExcel(sheetName="muscles").values]
        self.exercises = [Exercise(line) for line in self._readExcel(sheetName="exercises").values]

    def _checkExerciseMuscles(self):
        for i in range(len(self.exercises)):
            exercise = self.exercises[i]
            if exercise.checkMuscles(self.muscles) == False:
                print(f"Some error regarding exercise #{i} and it's corresponding muscles")

    def _readExcel(self, sheetName):
        return pd.read_excel(self.path, sheet_name=sheetName)



