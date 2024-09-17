import pandas as pd


class Exercise:
    def __init__(self, excelLine):
        self.name = excelLine[0]
        if pd.isna(excelLine[1]):
            self.description = ""
        else:
            self.description = excelLine[1]

        try:
            self.primaryMuscle = excelLine[2]
        except:
            self.primaryMuscle = ""

        try:
            self.secondaryMuscles = [muscle.strip() for muscle in excelLine[3].split(',')]
        except:
            self.secondaryMuscles = []

        self.focus = excelLine[4]

        try:
            if pd.isna(excelLine[5]):
                self.equipment = ""
            else:
                self.equipment = excelLine[5]
        except:
            self.equipment = ""

    def __str__(self):
        return self.name + ' - ' + self.description + ' - ' + self.primaryMuscle + ' - ' + self.primaryMuscle + ' - ' + ','.join(self.secondaryMuscles) + ' - ' + ','.join(self.equipment)

    def checkMuscles(self, muscleList):
        muscleGroups = [muscle.group for muscle in muscleList]
        muscleList = [muscle.name for muscle in muscleList]
        if self.primaryMuscle not in muscleGroups:
            print(self.primaryMuscle)
            return False
        if self.focus not in muscleList:
            print(self.focus)
            return False
        for sm in self.secondaryMuscles:
            if sm not in muscleList:
                print(self.secondaryMuscles)
                return False

        return True
    
    def checkEquipment(self):
        equipmentList = ["", "barbell", "dumbbells", "cable", "machine", "smith machine", "ez-bar", "t-bar", "trap bar", "parallel bars", "high bars", "low bars", "bench", "ab wheel"]
        if self.equipment not in equipmentList:
            print(self.equipment)
            return False
        
        return True



class ExerciseEntity:
    idCount = 1
    def __init__(self, exercise: Exercise):
        self.id = ExerciseEntity.idCount
        ExerciseEntity.idCount += 1
        self.name = exercise.name
        self.description = exercise.description
        self.equipment = exercise.equipment