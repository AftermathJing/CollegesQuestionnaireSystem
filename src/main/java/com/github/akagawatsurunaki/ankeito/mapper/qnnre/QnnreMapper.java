package com.github.akagawatsurunaki.ankeito.mapper.qnnre;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.akagawatsurunaki.ankeito.common.enumeration.QnnreStatus;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Qnnre;
import lombok.val;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface QnnreMapper extends BaseMapper<Qnnre> {


    default int updateQnnreStatusById(@NonNull String qnnreId, @NonNull QnnreStatus qnnreStatus) {
        return update(null, new UpdateWrapper<Qnnre>().lambda()
                .eq(Qnnre::getId, qnnreId)
                .set(Qnnre::getQnnreStatus, qnnreStatus));
    }

    default List<Qnnre> selectByProjectId(@NonNull String projectId) {
        return selectList(new QueryWrapper<Qnnre>().lambda().eq(Qnnre::getProjectId, projectId));
    }


}
