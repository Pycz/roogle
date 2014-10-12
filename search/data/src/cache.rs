extern crate rustdoc;
extern crate syntax;

use rustdoc::clean;
use rustdoc::fold::DocFolder;
use syntax::ast;
use std::collections::HashMap;


#[deriving(Clone)]
pub struct Cache {
    pub public_item: Option<clean::Item>,
    pub typarams: HashMap<ast::DefId, String>,
}


impl Cache {
    pub fn generics(&mut self, generics: &clean::Generics) {
        println!("generics");
        for typ in generics.type_params.iter() {
            println!("Hello: {}", typ.name);
            self.typarams.insert(typ.did, typ.name.clone());
        }
    }
}


impl DocFolder for Cache {
    fn fold_item(&mut self, item: clean::Item) -> Option<clean::Item> {

        match item.inner {
            clean::StructItem(ref s)          => self.generics(&s.generics),
            clean::EnumItem(ref e)            => self.generics(&e.generics),
            clean::FunctionItem(ref f)        => self.generics(&f.generics),
            clean::TypedefItem(ref t)         => self.generics(&t.generics),
            clean::TraitItem(ref t)           => self.generics(&t.generics),
            clean::ImplItem(ref i)            => self.generics(&i.generics),
            clean::TyMethodItem(ref i)        => self.generics(&i.generics),
            clean::MethodItem(ref i)          => self.generics(&i.generics),
            clean::ForeignFunctionItem(ref f) => self.generics(&f.generics),
            _ => {}
        };

        self.fold_item_recur(item)
    }
}


local_data_key!(pub cache_key: Cache)
