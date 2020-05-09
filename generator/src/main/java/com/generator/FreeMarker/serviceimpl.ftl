//package自定义
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
* Author:${Author}
* Date:${Date}
* 表注释:${Comment}
*/
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service{

    @Autowired
    private ${ClassName}Mapper mapper;

    /**
    * 新增${ClassName}
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int add${ClassName}(${ClassName} bean){
        int res=0;
 		try {
 			if(bean != null){
                res=mapper.insert${ClassName}(bean);
 			}
 		} catch (Exception e) {
             //手动事务回滚
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 	    }
 		return res;
    }

    /**
    * 删除${ClassName}
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int remove${ClassName}(${ClassName} bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.delete${ClassName}(bean);
            }
        } catch (Exception e) {
        //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 修改${ClassName}
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int modify${ClassName}(${ClassName} bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.update${ClassName}(bean);
            }
        } catch (Exception e) {
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 根据Map里的参数，查询${ClassName}集合
    */
    @Override
    public List<${ClassName}> query${ClassName}(Map params){
        List<${ClassName}> list = null;
        try {
            if(params != null){
                 list=mapper.select${ClassName}ByParams(params);
            }
        } catch (Exception e) {
            //error
            System.err.println(e.getMessage());
        }
        return list;
    }

}