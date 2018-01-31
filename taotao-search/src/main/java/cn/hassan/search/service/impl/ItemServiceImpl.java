package cn.hassan.search.service.impl;

import cn.hassan.search.mapper.ItemMapper;
import cn.hassan.search.service.ItemService;
import cn.hassan.search.vo.Item;
import com.taotao.common.pojo.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;
    /**
     * 导入所有的数据
     * @return
     */
    @Override
    public TaotaoResult importAllItems() {
        List<Item> itemList = itemMapper.getItemList();

        try {
            for (Item item : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_des());
                solrServer.add(document);
            }
            solrServer.commit();
            return TaotaoResult.ok();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
