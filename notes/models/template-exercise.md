# TemplateExercise
Cross-table between [Template](template.md) and [Exercise](exercise.md)
It stores an Exercise being part of a Template, along with its position in the workout.

### Fields
- id (number, primary key)
- templateId (number, references Template)
- exerciseId (number, references Exercise)
- index (number 0..)
