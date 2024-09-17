import pandas as pd

class Muscle:
    def __init__(self, excelLine):
        self.group = excelLine[0]
        self.name = excelLine[1]
        try:
            self.scientificName = excelLine[2]
        except:
            self.scientificName = ""

    def __str__(self):
        return self.name

class MuscleEntity:
    idCount = 1
    def __init__(self, muscle: Muscle):
        self.id = MuscleEntity.idCount
        MuscleEntity.idCount += 1
        self.group = muscle.group
        self.name = muscle.name
        self.scientificName = muscle.scientificName
