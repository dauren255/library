package kz.iitu.library.models;

import io.swagger.annotations.ApiModelProperty;

public enum Status {

    @ApiModelProperty(notes = "This status names")
    AVAILABLE, REQUESTED, ISSUED, OVERDUE;

    public String getStatus(){
        return name();
    }
}
