extern crate debug;
extern crate rustdoc;
extern crate rustc;
extern crate syntax;
extern crate serialize;

use rustdoc::core;
use rustdoc::clean;
use std::collections::HashMap;
use serialize::json::{Json, ToJson};
use rustdoc::fold::DocFolder;
mod cache;
mod json;


fn main() {
    let cratefile = "/home/marsel/programming/rtest/src/lib.rs";
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
        public_item: krate.module.clone(),
        typarams: analysis.external_typarams.borrow_mut().take().unwrap(),
    };

    cache.fold_crate(krate);
    cache::cache_key.replace(Some(cache.clone()));

    println!("{}", json::RItem(cache.public_item.unwrap().clone())
             .to_json().to_pretty_str());
}
