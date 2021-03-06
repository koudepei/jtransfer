package transfer.compile;

import com.jake.common.util.asm.util.AsmUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * 预编译解码器上下文
 * @author Jake
 *
 */
public class AsmDeserializerContext implements Opcodes {

    /**
     * 类名
     */
    private final String className;

    /**
     * ClassWriter
     */
    private final ClassWriter classWriter;

    /**
     * 自增的方法Id
     */
    private int methodId = 1;


    /**
     * 构造方法
     * @param className
     * @param classWriter
     */
    public AsmDeserializerContext(String className, ClassWriter classWriter) {
        this.className = className;
        this.classWriter = classWriter;
    }


    /**
     * 执行下一个编码方法
     * @return
     * @param name
     * @param curMethodVisitor 
     */
    public MethodVisitor invokeNextDeserialize(String name, MethodVisitor curMethodVisitor) {

        if (name == null) {
            name = "default";
        }
        
        String newMethodName = "deserialze_" + name + "_" + (methodId ++);
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, newMethodName, "(Ltransfer/Inputable;Ljava/lang/reflect/Type;BLtransfer/core/DeserialContext;)Ljava/lang/Object;", "<T:Ljava/lang/Object;>(Ltransfer/Inputable;Ljava/lang/reflect/Type;BLtransfer/core/DeserialContext;)TT;", null);
        mv.visitCode();

        curMethodVisitor.visitMethodInsn(INVOKEVIRTUAL, AsmUtils.toAsmCls(className), newMethodName, "(Ltransfer/Inputable;Ljava/lang/reflect/Type;BLtransfer/core/DeserialContext;)Ljava/lang/Object;", false);
        return mv;
    }

    		
	public ClassWriter getClassWriter() {
		return classWriter;
	}


    public String getClassName() {
        return className;
    }
	
	
}
