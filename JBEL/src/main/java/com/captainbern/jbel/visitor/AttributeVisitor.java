package com.captainbern.jbel.visitor;

import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.*;
import com.captainbern.jbel.commons.attribute.Deprecated;

public class AttributeVisitor {

    protected int api;

    protected AttributeVisitor attributeVisitor;

    public AttributeVisitor(int api, AttributeVisitor attributeVisitor) {
        if(api != Opcode.JBEL_1) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.attributeVisitor = attributeVisitor;
    }

    public void visitSourceFile(SourceFile sourceFile) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitSourceFile(sourceFile);
        }
    }

    public void visitSourceDebugExtension(SourceDebugExtension sourceDebugExtension) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitSourceDebugExtension(sourceDebugExtension);
        }
    }

    public void visitConstantValue(ConstantValue constantValue) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitConstantValue(constantValue);
        }
    }

    public void visitCode(Code code) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitCode(code);
        }
    }

    public void visitExceptions(Exceptions exceptions) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitExceptions(exceptions);
        }
    }

    public void visitLineNumberTable(LineNumberTable lineNumberTable) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitLineNumberTable(lineNumberTable);
        }
    }

    public void visitLocalVariableTable(LocalVariableTable localVariableTable) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitLocalVariableTable(localVariableTable);
        }
    }

    public void visitInnerClass(InnerClass innerClass) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitInnerClass(innerClass);
        }
    }

    public void visitSynthetic(Synthetic synthetic) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitSynthetic(synthetic);
        }
    }

    public void visitDeprecated(Deprecated deprecated) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitDeprecated(deprecated);
        }
    }

    public void visitSignature(Signature signature) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitSignature(signature);
        }
    }

    public void visitStackMap(StackMap stackMap) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitStackMap(stackMap);
        }
    }

    public void visitRuntimeVisibleAnnotations(RuntimeVisibleAnnotations runtimeVisibleAnnotations) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitRuntimeVisibleAnnotations(runtimeVisibleAnnotations);
        }
    }

    public void visitRuntimeInvisibleAnnotations(RuntimeInvisibleAnnotations runtimeInvisibleAnnotations) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitRuntimeInvisibleAnnotations(runtimeInvisibleAnnotations);
        }
    }

    public void visitRuntimeVisibleParameterAnnotations(RuntimeVisibleParameterAnnotations runtimeVisibleParameterAnnotations) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitRuntimeVisibleParameterAnnotations(runtimeVisibleParameterAnnotations);
        }
    }

    public void visitRuntimeInvisibleParameterAnnotations(RuntimeInvisibleParameterAnnotations runtimeInvisibleParameterAnnotations) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitRuntimeInvisibleParameterAnnotations(runtimeInvisibleParameterAnnotations);
        }
    }

    public void visitAnnotationDefault(AnnotationDefault annotationDefault) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitAnnotationDefault(annotationDefault);
        }
    }

    public void visitLocalVariableTypeTable(LocalVariableTypeTable localVariableTypeTable) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitLocalVariableTypeTable(localVariableTypeTable);
        }
    }

    public void visitEnclosingMethod(EnclosingMethod enclosingMethod) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitEnclosingMethod(enclosingMethod);
        }
    }

    public void visitBootstrapMethod(BootstrapMethods bootstrapMethods) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitBootstrapMethod(bootstrapMethods);
        }
    }

    public void visitStackMapTable(StackMapTable stackMapTable) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitStackMapTable(stackMapTable);
        }
    }

    public void visitMethodParameters(MethodParameters methodParameters) {
        if(this.attributeVisitor != null) {
            this.attributeVisitor.visitMethodParameters(methodParameters);
        }
    }
}
