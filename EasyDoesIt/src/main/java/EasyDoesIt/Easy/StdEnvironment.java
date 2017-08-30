package EasyDoesIt.Easy;

import EasyDoesIt.Easy.AbstractSyntaxTrees.*;

public final class StdEnvironment {

//_________________________________________________________________________
//                        These are small ASTs representing standard types

    public static TypeDenoter booleanType;
    public static TypeDenoter charType;
    public static TypeDenoter integerType;
    public static TypeDenoter floatType;
    public static TypeDenoter realType;

    public static TypeDenoter anyType;
    public static TypeDenoter errorType;

    public static TypeDefinition booleanDecl;
    public static TypeDefinition integerDecl;
    public static TypeDefinition realDecl;
    public static TypeDefinition floatDecl;
    public static TypeDefinition charDecl;

//_________________________________________________________________________
//   These are small ASTs representing "declarations" of standard entities

    public static Declaration falseDecl;
    public static Declaration trueDecl;
    public static UnaryOperatorDefinition notDecl;
    public static BinaryOperatorDefinition andDecl;
    public static BinaryOperatorDefinition orDecl;

    public static BinaryOperatorDefinition moduloDecl;
    public static BinaryOperatorDefinition lessDecl;
    public static BinaryOperatorDefinition greaterDecl;
    public static BinaryOperatorDefinition notlessDecl;
    public static BinaryOperatorDefinition notgreaterDecl;

    public static BinaryOperatorDefinition addDecl;
    public static BinaryOperatorDefinition subtractDecl;
    public static BinaryOperatorDefinition multiplyDecl;
    public static BinaryOperatorDefinition divideDecl;

    public static BinaryOperatorDefinition equalDecl;
    public static BinaryOperatorDefinition unequalDecl;

    public static FunctionDefinition substrDecl;
    public static FunctionDefinition lengthDecl;
    public static BinaryOperatorDefinition concatDecl;
}
