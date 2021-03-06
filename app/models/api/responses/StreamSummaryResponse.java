/*
 * Copyright 2013 TORCH UG
 *
 * This file is part of Graylog2.
 *
 * Graylog2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog2.  If not, see <http://www.gnu.org/licenses/>.
 */
package models.api.responses;

import com.google.gson.annotations.SerializedName;
import models.StreamRule;

import java.util.List;

public class StreamSummaryResponse {

	public String id;
	public String title;
	
	@SerializedName("created_at")
	public TimestampResponse createdAt;
	
	@SerializedName("creator_user_id")
	public String creatorUserId;

    @SerializedName("rules")
    public List<StreamRuleSummaryResponse> streamRules;
	
	// public List<StreamRuleSummary> rules;
	
}
