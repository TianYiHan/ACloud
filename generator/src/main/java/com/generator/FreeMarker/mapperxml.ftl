<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.generator.template.mapper.${ClassName}Mapper">



    <!-- 结果集 -->
    <resultMap id="${ClassName}ResultMap" type="com.generator.template.entity.${ClassName}">
        <#list dbs as item>
            <result column="${item.field}" property="${item.field}" javaType="<#if (item.type) =='String'>java.lang.String<#elseif (item.type) =='Date'>java.util.Date<#elseif (item.type) =='Double'>java.lang.Double<#elseif (item.type) =='int'>java.lang.Integer</#if>" /><!-- ${item.comment} -->
        </#list>
    </resultMap>

    <!-- 新增信息 -->
    <insert id="insert${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">

    </insert>


    <!-- 删除信息 -->
    <delete id="delete${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">

    </delete>

    <!-- 更改信息 -->
    <update id="update${ClassName}"  parameterType="com.generator.template.entity.${ClassName}">

    </update>

    <!-- 查询信息 -->
    <select id="select${ClassName}ByParams" parameterType="map" resultType="com.generator.template.entity.${ClassName}">

    </select>

</mapper>
