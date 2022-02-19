/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.manager.common.pojo.datastorage.kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.inlong.manager.common.enums.BizErrorCodeEnum;
import org.apache.inlong.manager.common.exceptions.BusinessException;

/**
 * Kafka storage info
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaStorageDTO {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ApiModelProperty("Kafka bootstrap servers")
    private String address;

    @ApiModelProperty("Kafka topicName")
    private String topicName;

    @ApiModelProperty("Data Serialization, support: Json, Canal, Avro")
    private String serializationType;

    /**
     * Get the dto instance from the request
     */
    public static KafkaStorageDTO getFromRequest(KafkaStorageRequest request) {
        return KafkaStorageDTO.builder()
                .address(request.getAddress())
                .topicName(request.getTopicName())
                .serializationType(request.getSerializationType())
                .build();
    }

    public static KafkaStorageDTO getFromJson(@NotNull String extParams) {
        try {
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return OBJECT_MAPPER.readValue(extParams, KafkaStorageDTO.class);
        } catch (Exception e) {
            throw new BusinessException(BizErrorCodeEnum.STORAGE_INFO_INCORRECT.getMessage());
        }
    }
}
