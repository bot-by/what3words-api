/*
 * Copyright 2021 Witalij Berdinskich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Unofficial Java wrapper for <em>what3words</em> API to convert 3 word addresses to coordinates and vice versa.
 *
 * @since 1.0.0
 * @see <a href="https://what3words.com/about-us/"><em>what3words</em> /// The simplest way to talk about location</a>
 */
module bot_by.what3words_api {
	exports uk.bot_by.w3w;
	opens uk.bot_by.w3w;
	requires static org.jetbrains.annotations;
	requires org.slf4j;
	requires org.json;
	requires feign.core;
}