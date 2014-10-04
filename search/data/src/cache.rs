extern crate rustdoc;
extern crate syntax;

use rustdoc::clean;
use rustdoc::fold::DocFolder;
use syntax::ast;
use std::collections::HashMap;


#[deriving(Clone)]
pub struct Cache {
    pub public_items: Vec<clean::Item>,
}


impl DocFolder for Cache {
    fn fold_item(&mut self, item: clean::Item) -> Option<clean::Item> {
        if item.visibility != Some(ast::Public) { return None; }
        self.public_items.push(item.clone());
        Some(item)
    }
}


local_data_key!(pub typarams: HashMap<ast::DefId, String>)
