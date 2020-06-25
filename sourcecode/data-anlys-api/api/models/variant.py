class Variant:
    level = None
    typeCode = None
    typeDesc = None
    numOfVariant = None
    rateOfVariant = None

    def __init__(self, level, code, desc, num, rate):
        self.level = level
        self.typeCode = code
        self.typeDesc = desc
        self.numOfVariant = num
        self.rateOfVariant = rate
