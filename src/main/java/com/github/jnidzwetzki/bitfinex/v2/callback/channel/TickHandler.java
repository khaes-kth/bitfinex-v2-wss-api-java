/*******************************************************************************
 *
 *    Copyright (C) 2015-2018 Jan Kristof Nidzwetzki
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *******************************************************************************/
package com.github.jnidzwetzki.bitfinex.v2.callback.channel;

import java.math.BigDecimal;

import org.json.JSONArray;

import com.github.jnidzwetzki.bitfinex.v2.BitfinexApiBroker;
import com.github.jnidzwetzki.bitfinex.v2.entity.APIException;
import com.github.jnidzwetzki.bitfinex.v2.entity.BitfinexTick;
import com.github.jnidzwetzki.bitfinex.v2.entity.symbol.BitfinexStreamSymbol;
import com.github.jnidzwetzki.bitfinex.v2.entity.symbol.BitfinexTickerSymbol;

public class TickHandler implements ChannelCallbackHandler {

	/**
	 * Handle a tick callback
	 * @param channel
	 * @param subarray
	 */
	@Override
	public void handleChannelData(final BitfinexApiBroker bitfinexApiBroker,
			final BitfinexStreamSymbol channelSymbol, final JSONArray jsonArray) throws APIException {

		final BitfinexTickerSymbol currencyPair = (BitfinexTickerSymbol) channelSymbol;

		// 0 = BID
	    // 1 = BID SIZE
		// 2 = ASK
	    // 3 = ASK SIZE
	    // 4 = Daily Change
	    // 5 = Daily Change %
		// 6 = Last Price
	    // 7 = Volume
	    // 8 = High
	    // 9 = Low

		final BigDecimal bid = jsonArray.getBigDecimal(0);
		final BigDecimal bidSize = jsonArray.getBigDecimal(1);
		final BigDecimal ask = jsonArray.getBigDecimal(2);
		final BigDecimal askSize = jsonArray.getBigDecimal(3);
		final BigDecimal dailyChange = jsonArray.getBigDecimal(4);
		final BigDecimal dailyChangePerc = jsonArray.getBigDecimal(5);
		final BigDecimal price = jsonArray.getBigDecimal(6);
		final BigDecimal volume = jsonArray.getBigDecimal(7);
		final BigDecimal high = jsonArray.getBigDecimal(8);
		final BigDecimal low = jsonArray.getBigDecimal(9);

		final BitfinexTick tick = new BitfinexTick(bid, bidSize, ask, askSize, dailyChange, dailyChangePerc,
				price, volume, high, low);

		bitfinexApiBroker.getQuoteManager().handleNewTick(currencyPair, tick);
	}
}
