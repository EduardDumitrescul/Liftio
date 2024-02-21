import pandas as pd

def readExcel(path, sheetName=None):
    return pd.read_excel(path, sheet_name=sheetName)


