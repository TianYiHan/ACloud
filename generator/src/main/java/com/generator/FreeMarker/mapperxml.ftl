<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.generator.template.mapper.${ClassName}Mapper">



    <!-- 结果集 -->
    <resultMap id="${ClassName}ResultMap" type="com.generator.template.entity.${ClassName}">
        <#list dbs as item>
            <result column="${item.field}" property="${item.field}" javaType="<#if (item.type) =='String'>java.lang.String<#elseif (item.type) =='Date'>java.util.Date<#elseif (item.type) =='Double'>java.lang.Double<#elseif (item.type) =='int'>java.lang.Integer</#if>" /><!-- ${item.comment} -->
        </#list>
    </resultMap>

    <!-- 查询字段 -->
    <sql id="${ClassName}_Column_List">
        <#list dbs as item>`${item.field?capitalize}`<#if item_has_next>,</#if></#list>
    </sql>

    <!-- 新增信息 -->
    <insert id="insert${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">
        insert into ${ClassName?cap_first} (<#list dbs as item>`${item.field}`<#if item_has_next>,</#if></#list>) values (<#list dbs as item><#noparse>#{</#noparse>${item.field}}<#if item_has_next>,</#if></#list>)
    </insert>


    <!-- 删除信息 -->
    <delete id="delete${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">
        delete from ${ClassName?cap_first} where ididid???????didiidididi = <#noparse>#{</#noparse>ididiidididiidiidi}
    </delete>

    <!-- 更改信息 -->
    <update id="update${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">
        update
        ${ClassName?cap_first}
        set
        <trim suffixOverrides=",">
            <#list dbs as item>
                <if test="${item.field} != null">
                    ${item.field} = <#noparse>#{</#noparse>${item.field}},
                </if>
            </#list>
        </trim>
        where
        idididii???????dididiidididi = <#noparse>#{</#noparse>ididiidididiidiidi}
    </update>

    <!-- 查询信息 -->
    <select id="select${ClassName}ByParams" parameterType="java.util.Map" resultType="com.generator.template.entity.${ClassName}">
        select
        <include refid="${ClassName}_Column_List" />
        from ${ClassName?cap_first}
        <where>
            <trim prefixOverrides="and">
                <#list dbs as item>
                    <if test="${item.field} != null and ${item.field} != ''">
                        and
                        ${item.field} like CONCAT(CONCAT('%', <#noparse>#{</#noparse>${item.field}}), '%')
                    </if>
                </#list>
            </trim>
        </where>
    </select>

</mapper>
