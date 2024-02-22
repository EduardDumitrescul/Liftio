import pandas as pd


class Exercise:
    def __init__(self, excelLine):
        self.name = excelLine[0]
        if pd.isna(excelLine[1]):
            self.description = ""
        else:
            self.description = excelLine[1]

        if pd.isna(excelLine[2]):
            self.primaryMuscle = ""
        else:
            self.primaryMuscle = excelLine[2]
        try:
            self.secondaryMuscles = [muscle.strip() for muscle in excelLine[3].split(',')]
        except:
            self.secondaryMuscles = []
        try:
            self.equipment = [equipment.strip() for equipment in excelLine[4].split(',')]
        except:
            self.equipment = []

    def __str__(self):
        return self.name + ' - ' + self.description + ' - ' + self.primaryMuscle + ' - ' + ','.join(self.secondaryMuscles) + ' - ' + ','.join(self.equipment)

    def checkMuscles(self, muscleList):
        ml = [muscle.name for muscle in muscleList]
        if self.primaryMuscle not in ml:
            return False
        for sm in self.secondaryMuscles:
            if sm not in ml:
                return False

        return True


class ExerciseEntity:
    idCount = 0
    def __init__(self, exercise: Exercise):
        self.id = ExerciseEntity.idCount
        ExerciseEntity.idCount += 1
        self.name = exercise.name
        self.description = exercise.description