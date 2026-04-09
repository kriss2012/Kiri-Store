package zed.rainxch.details.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class AttestationsResponse(
    val attestations: List<JsonObject> = emptyList(),
)
