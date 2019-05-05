package com.InfoWeb.demo.model.MybatisPlus;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author bockey
 */
@Data
@TableName
public class Songs {

    @TableId(type = IdType.AUTO)
    Integer id;
    @TableField(strategy = FieldStrategy.NOT_EMPTY)
    String name;
    @TableField(select = false, fill = FieldFill.DEFAULT)
    String lyrics;
    String pics;
    String author;
    String singer;
    String track;

    @EnumValue()
    SongType type;

    enum SongType {
        PUREMUSIC(0, ""), SINGING(1, ""),
        BACKMUSIC(2, ""), NATURESOUND(3, "");
        public final int code;
        public final String desc;

        SongType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
