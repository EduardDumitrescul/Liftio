class ExerciseMuscleEntity:
    def __init__(self, exerciseId, muscleId, isPrimary=False):
        self.exerciseId = exerciseId
        self.muscleId = muscleId
        self.isPrimary = isPrimary


def createExerciseMuscleEntities(exercises, exerciseEntities, muscleEntities):
    muscleDict = dict()
    for muscle in muscleEntities:
        muscleDict[muscle.name] = muscle.id

    exDict = dict()
    for exercise in exerciseEntities:
        exDict[exercise.name] = exercise.id

    # ExerciseMuscleEntity list
    em = []

    for exercise in exercises:
        em.append(ExerciseMuscleEntity(exDict[exercise.name], muscleDict[exercise.focus], True))
        for muscle in exercise.secondaryMuscles:
            em.append(ExerciseMuscleEntity(exDict[exercise.name], muscleDict[muscle], False))

    return em

