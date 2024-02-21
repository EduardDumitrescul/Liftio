from excelReader import ExcelReader

reader = ExcelReader('../data/db-preload.xlsx')

print(len(reader.exercises))

