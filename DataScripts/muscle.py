class Muscle:
    def __init__(self, excelLine):
        self.name = excelLine[0]

    def __str__(self):
        return self.name

