import pandas as pd

from exercise import Exercise
from muscle import Muscle


class ExcelReader:
    def __init__(self, path):
        self.path = path
        self._readData()

    def _readData(self):
        self.muscles = [Muscle(line) for line in self._readExcel(sheetName="muscles").values]
        self.exercises = [Exercise(line) for line in self._readExcel(sheetName="exercises").values]

    def _readExcel(self, sheetName):
        return pd.read_excel(self.path, sheet_name=sheetName)



