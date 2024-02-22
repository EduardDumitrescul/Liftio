class Muscle:
    def __init__(self, excelLine):
        self.name = excelLine[0]

    def __str__(self):
        return self.name

class MuscleEntity:
    idCount = 0
    def __init__(self, muscle: Muscle):
        self.id = MuscleEntity.idCount
        MuscleEntity.idCount += 1
        self.name = muscle.name
