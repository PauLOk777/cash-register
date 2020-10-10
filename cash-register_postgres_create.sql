CREATE TABLE "users" (
	"id" SERIAL PRIMARY KEY ,
	"first_name" VARCHAR(60) NOT NULL,
	"last_name" VARCHAR(60) NOT NULL,
	"username" VARCHAR(20) NOT NULL,
	"password" VARCHAR(255) NOT NULL,
	"email" VARCHAR(255) NOT NULL,
	"phone_number" VARCHAR(13) NOT NULL,
	"role" VARCHAR(50)
);

CREATE TABLE "order" (
	"id" SERIAL PRIMARY KEY,
	"user_id" INTEGER NOT NULL DEFAULT -1,
	"total_price" INTEGER NOT NULL,
	"status" VARCHAR(30) NOT NULL,
	"create_date" TIMESTAMP NOT NULL
);

CREATE TABLE "order_products" (
	"order_id" INTEGER NOT NULL,
	"product_id" INTEGER NOT NULL,
	"amount" INTEGER NOT NULL,
	UNIQUE (order_id, product_id)
);

CREATE TABLE "products" (
	"code" INTEGER PRIMARY KEY,
	"name" VARCHAR(255) NOT NULL,
	"price" INTEGER NOT NULL,
	"amount" INTEGER NOT NULL,
	"measure" VARCHAR(20) NOT NULL
);

ALTER TABLE "users" ADD CONSTRAINT "users_fk0" FOREIGN KEY ("role_id") REFERENCES "roles"("id") ON DELETE SET DEFAULT;

ALTER TABLE "order" ADD CONSTRAINT "order_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE SET NULL;

ALTER TABLE "order_products" ADD CONSTRAINT "order_products_fk0" FOREIGN KEY ("order_id") REFERENCES "order"("id") ON DELETE CASCADE;
ALTER TABLE "order_products" ADD CONSTRAINT "order_products_fk1" FOREIGN KEY ("product_id") REFERENCES "products"("code") ON DELETE CASCADE;