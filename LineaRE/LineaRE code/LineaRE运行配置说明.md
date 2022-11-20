# LineaRE运行配置说明

LineaRE：一种用于链接预测的简单而又强大的知识图谱嵌入模型

## 代码
#### 运行LineaRE的步骤：

1. 设置 json 文件夹里 <code> ./config/*.json </code>文件的参数, 以运行FB15k数据集为例，则需要设置 <code> config_FB15k.json </code>文件的参数，将存储文件的路径以及要保存文件的路径修改为自己对应目录下文件的路径；
2. 运行<code>code</code>文件夹下的<code>  main.py </code>文件。
#### 代码文件
<code>code</code>文件下目录下一共有6个<code>.py</code>文件，分别说明如下：

* <code> configure.py</code>: 包括所有超参数， 从<code> ./config/*.json </code>文件中读取参数;
* <code> data.py</code>: 数据加载器， 是一个包含数据集中所有数据的KG类;
* <code> lineare.py</code>: LineaRE模型的实现；
* <code> main.py</code>: 负责整个程序的录入，创建KG对象、训练测试对象，并开始训练和测试对象;
* <code> traintest.py</code>: 描述接收KG对象，模型的训练和测试的过程;
* <code> utils.py</code>: 一些辅助建模的工具。
#### 依赖
* Python 3
* PyTorch 
* Numpy

## 数据集
<code>data</code>文件夹下一共有四个基准数据集，包括FB15k, WN18, FB15k-237和WN18RR。

其中每个数据集文件夹下都有对应的数据文件，说明如下：

 - *entities.dict*: 字典将实体映射到唯一id；
 - *relations.dict*: 字典将关系映射到唯一id；
 - *train.txt*: 训练KGE模型以拟合该数据集；
 - *valid.txt*: 如果没有可用的验证数据，则创建空白文件；
 - *test.txt*: 在这个数据集上评估KGE模型。

## 参数(以./config/config_FB15k.json为例)

> 参照源代码以及论文
>
> >  - *dim*: the dimmention (size) of embeddings；
> >  - *norm_p*: L1-Norm or L2-Norm；
> >  - *alpha*: the temperature of Self-Adversarial Negative Sampling [[1](#refer-1)], Eq(3) in paper；
> >  - *beta*: a hyper-parameter in softplus(pytorch), Eq(4)；
> >  - *gamma*: a hyper-parameter in loss the function, Eq(5), used to separate the positive sample from the negative sample；
> >  - *learning_rate*: initial learning rate, decaying during training；
> >  - *decay_rate*: learning rate decay rate；
> >  - *batch_size*：Number of parameters passed to the program for training at a time（单次传递给程序用以训练的参数个数）;
> >  - *neg_size*: the number of negative samples for each positive sample in an optimization step;
> >  - *regularization*: the regularization coefficient;
> >  - *drop_rate*: some dimensions of embeddings are randomly dropped with probability 'drop_rate';
> >  - *test_batch_size*:；
> >  - *data_path*: "./data_path/data/FB15k"（需要修改为自己文件路径）；
> >  - *save_path*: "./save_path/FB15k"（需要修改为自己文件路径）;
> >  - *max_step*: total training steps;
> >  - *valid_step*: valid the model every 'valid_step' training steps;
> >  - *log_step*: logging the average loss value every 'log_step' training steps;
> >  - *test_log_step*:;
> >  - *optimizer*: SGD, Adam ...（优化器）;
> >  - *init_checkpoint*: whether load model from checkpoint file or not;
> >  - *use_old_optimizer*: if init_checkpoint is True, load the stored optimizer or use a new optimizer;
> >  - *sampling_rate*: assign a weight for each triple, like word2vec;
> >  - *sampling_bias*: assign a weight for each triple, like word2vec;
> >  - *device*: 'cuda:0', cpu...;
> >  - *multiGPU*: use multiple GPUs;
> >

<div id="refer-1"></div>
