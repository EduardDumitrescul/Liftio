# Set
Model that represents a single set inside a [TemplateExercise](template-exercise.md).
It contains the reps, weight and the index for ordering.

### Attributes
- id (number, primary key)
- templateExerciseId (number, references TemplateExercise)
- index (number 0..)
- reps (number)
- weight (number, in kg)

