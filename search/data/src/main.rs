extern crate debug;
extern crate rustdoc;
extern crate rustc;
extern crate syntax;
extern crate serialize;


use std::sync::Arc;
use rustdoc::core;
use rustdoc::clean::{StructItem, EnumItem, FunctionItem, ModuleItem, TypedefItem
                     , StaticItem, TraitItem, ImplItem, ViewItemItem, TyMethodItem
                     , MethodItem, StructFieldItem, VariantItem, MacroItem
                     , ForeignFunctionItem, ForeignStaticItem, PrimitiveItem
                     , AssociatedTypeItem, Item, ItemEnum, Type};
use rustdoc::clean;
use rustdoc::html::item_type::{ItemType, shortty};
use rustdoc::html::item_type;
use rustdoc::fold::DocFolder;
use syntax::ast;
use std::collections::HashMap;
use rustc::util::nodemap::NodeSet;
mod cache;
mod json;


fn main() {
    let cratefile = "/home/marsel/programming/rust/src/libcore/lib.rs";
    let cr = Path::new(cratefile);
    let libs: Vec<Path> = vec![Path::new("/usr/local/lib/rustlib/i686-unknown-linux-gnu/lib")];
    let cfgs: Vec<String> = vec![];
    let externs: core::Externs = HashMap::new();
    println!("starting to run rustc");
    let (mut krate, analysis) = std::task::try(proc() {
        let cr = cr;
        core::run_core(libs, cfgs, externs, &cr, None)
    }).map_err(|boxed_any|format!("{:?}", boxed_any)).unwrap();
    println!("finished with rustc");

    let mut cache = cache::Cache {
        public_items: vec![],
    };
    let typs = analysis.external_typarams.borrow_mut().take();
    cache::typarams.replace(typs.clone());
    println!("typarams: {}", typs.unwrap().len());

    cache.fold_crate(krate);
    println!("{}", json::items_to_json(&cache.public_items));
}
