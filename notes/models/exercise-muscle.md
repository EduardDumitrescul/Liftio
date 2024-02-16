# ExerciseMuscle
Cross-table for the relationship between [Exercise](exercise.md) and [Muscle](muscle.md)

### Attributes
- exerciseId (number, references Exercise, PK)
- muscleId (number, references Muscle, PK)
- isPrimary (bool)
  - marks that the current Muscle is the Target Muscle for the current Exercise
  - it can only be one primary 
